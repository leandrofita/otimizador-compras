package com.app.otimizador_compras.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.otimizador_compras.domain.Adress;
import com.app.otimizador_compras.domain.Client;
import com.app.otimizador_compras.model.ClientDTO;
import com.app.otimizador_compras.model.CompleteClientDTO;
import com.app.otimizador_compras.repos.AdressRepository;
import com.app.otimizador_compras.repos.ClientRepository;


@Service
public class ClientService {

    private final ClientRepository clientRepository;
    
    private final AdressRepository adressRepository;

    public ClientService(final ClientRepository clientRepository, AdressRepository adressRepository) {
        this.clientRepository = clientRepository;
		this.adressRepository = adressRepository;
    }

    public List<ClientDTO> findAll() {
        return clientRepository.findAll(Sort.by("id"))
                .stream()
                .map(client -> mapToDTO(client, new ClientDTO()))
                .collect(Collectors.toList());
    }

    public ClientDTO get(final Long id) {
        return clientRepository.findById(id)
                .map(client -> mapToDTO(client, new ClientDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public CompleteClientDTO create(final CompleteClientDTO clientDTO) {
        final Client client = new Client();
        final Adress adress = new Adress();
        Long clientId;
        
        final Client existClient = clientRepository.findByEmail(clientDTO.getEmail());
        //criando o client
        if(existClient == null) {
        	client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setAvatar(clientDTO.getAvatar());
           clientId = clientRepository.save(client).getId();
        } else {
        	throw new DataIntegrityViolationException("This email is already registered");
        }
        
      //criando o adress
        adress.setStreet(clientDTO.getStreet());
        adress.setNumber(clientDTO.getNumber());
        adress.setDistrict(clientDTO.getDistrict());
        adress.setZipCode(clientDTO.getZipCode());
        Optional<Client> recoveredClient = clientRepository.findById(clientId);
        if(recoveredClient.isPresent()) {
        	adress.setClient(recoveredClient.get());
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found");
        }
        Long adressId = adressRepository.save(adress).getId();
        
        Optional<Adress> recoverdAdress = adressRepository.findById(adressId);
        
        if(recoverdAdress.isPresent()) {
        	return new CompleteClientDTO(recoveredClient.get(), recoverdAdress.get());
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adress not found");
        }
        
    }

    public void update(final Long id, final ClientDTO clientDTO) {
        final Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(clientDTO, client);
        clientRepository.save(client);
    }

    public void delete(final Long id) {
        clientRepository.deleteById(id);
    }

    private ClientDTO mapToDTO(final Client client, final ClientDTO clientDTO) {
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setAvatar(client.getAvatar());
        clientDTO.setAddress(client.getAddress().getId());
        return clientDTO;
    }

    private Client mapToEntity(final ClientDTO clientDTO, final Client client) {
        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setAvatar(clientDTO.getAvatar());
        final Adress adress = clientDTO.getAddress() == null ? null : adressRepository.findById(clientDTO.getAddress())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Adress not found"));
        client.setAddress(adress);
        return client;
    }

    public boolean emailExists(final String email) {
        return clientRepository.existsByEmailIgnoreCase(email);
    }

}
