/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package njt.mavenproject1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import njt.mavenproject1.dto.impl.FlowerDto;
import njt.mavenproject1.servis.FlowerServis;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Korisnik
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/flower")
public class FlowerControler {
    private final FlowerServis flowerServis;

    public FlowerControler(FlowerServis flowerServis) {
        this.flowerServis = flowerServis;
    }
    
    //ucitavanje iz baze
    @GetMapping
    @Operation(summary = "Retrieve all Flower entities.")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = FlowerDto.class), mediaType = "application/json")})
    public ResponseEntity<List<FlowerDto>> getAll(){
        return new ResponseEntity<>(flowerServis.findAll(), HttpStatus.OK);
    }
    
    @PostMapping
    @Operation(summary = "Create new flower entity.")
    @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = FlowerDto.class), mediaType = "application/json")})
    public ResponseEntity<FlowerDto> addCategory(@Valid @RequestBody @NotNull FlowerDto flowerDto){
        try{
            FlowerDto saved = flowerServis.create(flowerDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
            //nakon post on nam vrati to sto smo uneli jer smo stavili da kroz body vraca saved
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving flower");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value="id") Long id){
        try{
            flowerServis.deteleById(id);
            return new ResponseEntity<>("Flower successfully deleted.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Flower does not exist:"+id, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Flower entity.")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = FlowerDto.class), mediaType = "application/json")})
    public ResponseEntity<FlowerDto> updateCategory(@PathVariable Long id, @Valid @RequestBody FlowerDto flowerDto){
        try{
            flowerDto.setId(id);//azuruanje pravog entiteta
            FlowerDto updated = flowerServis.update(flowerDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while updating.");
        }
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Retrieve all products for a given restaurant.")
    public ResponseEntity<List<FlowerDto>> getByRestaurant(@PathVariable Long categoryId){
        List<FlowerDto> flowers = flowerServis.findByCategory(categoryId);
        return new ResponseEntity<>(flowers, HttpStatus.OK);
    }
    
}
