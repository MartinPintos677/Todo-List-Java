package br.com.martinpintos.todolist.user;

import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private IUserRepository userRepository;

@PostMapping("/")
public ResponseEntity Create(@RequestBody UserModel userModel) {
  var user = this.userRepository.findByUsername(userModel.getUsername());
    
  if (user != null) {
    System.out.println("Usuário já existe.");
    // Mensagem de erro
    // Status Code
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe.");
    
  }

  var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

  userModel.setPassword(passwordHashed);

    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.status(HttpStatus.OK).body(userCreated);
  }
}
