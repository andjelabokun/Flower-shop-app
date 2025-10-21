/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.mavenproject1.dto.impl.OrderDto;
import njt.mavenproject1.entity.impl.OrderStatus;
import njt.mavenproject1.servis.OrderServis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders")
public class OrderControler {
    private final OrderServis servis;

    public OrderControler(OrderServis servis) {
        this.servis = servis;
    }
    
    @GetMapping
    public ResponseEntity<List<OrderDto>> all(){
        return new ResponseEntity<>(servis.findAll(), HttpStatus.OK);
        //http response koji server vraca klijentu
        //iz servisa zovemo findall koji zove repository findall to je body parametar
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> byId (@PathVariable Long id){
        try{
            return new ResponseEntity<>(servis.findbyId(id), HttpStatus.OK);
        } catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    }
    
    @PostMapping
    @Operation(summary = "Create order with items in a single transaction.")
    @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = OrderDto.class), mediaType = "application/json")})
    public ResponseEntity<OrderDto> add(@Valid @RequestBody @NotNull OrderDto dto){
        try{
            OrderDto saved = servis.create(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
            //nakon post on nam vrati to sto smo uneli jer smo stavili da kroz body vraca saved
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving order");
        }
    }
    
    //patch i put se koristi za azuriranje 
    //put kada ceo objekat
    //patch samo jedno polje objekta - status
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status){
        try{
            return new ResponseEntity<>(servis.updateStatus(id, status), HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        try{
            servis.deteleById(id);
            return new ResponseEntity<>("Order successfully deleted.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Order does not exist:"+id, HttpStatus.NOT_FOUND);
        }
    }
    
   
}
