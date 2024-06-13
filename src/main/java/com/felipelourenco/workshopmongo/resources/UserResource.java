package com.felipelourenco.workshopmongo.resources;

import com.felipelourenco.workshopmongo.domain.User;
import com.felipelourenco.workshopmongo.dto.UserDTO;
import com.felipelourenco.workshopmongo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> findALl() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDto = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(usersDto);
    }

    @RequestMapping(value = "/{id}",method= RequestMethod.GET)
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
        User user = userService.fromDTO(userDTO);
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}",method= RequestMethod.DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
