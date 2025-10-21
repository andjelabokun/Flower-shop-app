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
import njt.mavenproject1.dto.impl.CategoryDto;
import njt.mavenproject1.servis.CategoryServis;
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
@RequestMapping("/api/category")
public class CategoryControler {
    //http zahtev ima link
    
    private final CategoryServis categoryServis;

    public CategoryControler(CategoryServis categoryServis) {
        this.categoryServis = categoryServis;
    }
    
    //koji zahtev hocemo da obradimo
    @GetMapping
    @Operation(summary = "Retrieve all Category entities.")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class), mediaType = "application/json")})
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>(categoryServis.findAll(), HttpStatus.OK);
        //http response koji server vraca klijentu
        //iz servisa zovemo findall koji zove repository findall to je body parametar
        //content anotacija je za header
        //najvazniji je getmapping
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById (
        @NotNull(message = "Not null or empty!")
        @PathVariable(value = "id") Long id)
        {
        try{
            return new ResponseEntity<>(categoryServis.findById(id), HttpStatus.OK);
        } catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expected something.");
    }
    }
    
    @PostMapping
    @Operation(summary = "Create new category entity.")
    @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = CategoryDto.class), mediaType = "application/json")})
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody @NotNull CategoryDto categoryDto){
        try{
            System.out.println(categoryDto);
            CategoryDto saved = categoryServis.create(categoryDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
            //nakon post on nam vrati to sto smo uneli jer smo stavili da kroz body vraca saved
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while saving category");
        }
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value="id") Long id){
        try{
            categoryServis.deteleById(id);
            return new ResponseEntity<>("Category successfully deleted.", HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>("Categpry does not exist:"+id, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing Category entity.")
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CategoryDto.class), mediaType = "application/json")})
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto){
        try{
            categoryDto.setId(id);//azuruanje pravog entiteta
            CategoryDto updated = categoryServis.update(categoryDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while updating.");
        }
    }
}
