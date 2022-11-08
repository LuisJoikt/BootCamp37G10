package nttdata.bootcamp.mscredits.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import nttdata.bootcamp.mscredits.interfaces.ITypeCreditService;
import nttdata.bootcamp.mscredits.model.TypeCredit;

@Slf4j
@RestController
public class TypeCreditController {
    @Autowired
    private ITypeCreditService service;

    @PostMapping("/types")
    public ResponseEntity<?> createFromList(@RequestBody List<TypeCredit> credits) {
        try {
            final List<TypeCredit> response = service.createFromList(credits);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @CircuitBreaker(name = "types", fallbackMethod = "findAllAlt")
    @GetMapping("/types")
    public ResponseEntity<?> findAll() {
        final List<TypeCredit> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> findAllAlt(Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(new ArrayList<TypeCredit>());
    }

    @CircuitBreaker(name = "types", fallbackMethod = "findByTypeAlt")
    @GetMapping("/types/{type}")
    public ResponseEntity<?> findByType(@PathVariable String type) {
        try {
            Optional<TypeCredit> resp = service.findByType(type);
            if (resp.isPresent()) {
                return ResponseEntity.ok(resp.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Nose encontró registro con el tipo: %s", type));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> findByTypeAlt(@PathVariable String type, Exception ex) {
        log.info(ex.getMessage());
        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/types")
    public ResponseEntity<?> update(@RequestBody TypeCredit typeCredit) {
        try {
            Optional<TypeCredit> typeValid = service.findById(typeCredit.getId());
            if (typeValid.isPresent()) {
                final TypeCredit resp = service.update(typeCredit);
                return ResponseEntity.ok().body(resp);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No se encontró el tipo (%:%s) que intenta actualizar.", typeCredit.getId(),
                            typeCredit.getType()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}