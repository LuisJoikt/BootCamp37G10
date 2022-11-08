package com.nttdata.bootcamp.msaccounts.application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.msaccounts.interfaces.IAccountService;
import com.nttdata.bootcamp.msaccounts.interfaces.IDebitCardService;
import com.nttdata.bootcamp.msaccounts.model.Account;
import com.nttdata.bootcamp.msaccounts.model.DebitCard;

import reactor.core.publisher.Mono;

@RestController
public class DebitCardController {

    @Autowired
    private IDebitCardService service;

    @Autowired
    private IAccountService accountService;

    @PostMapping("/assign_debitCard")
    public Mono<ResponseEntity<?>> assignCardToAccount(@RequestBody DebitCard debitCard) {
        try {
            Optional<Account> optAccount = accountService.findByNroAccount(debitCard.getNroAccount());//eliminar
            if (optAccount.isPresent()) { //elimi
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date expireDate = DateUtils.addYears(fmt.parse(LocalDate.now().toString()), 3);
                debitCard.setIsEnabled(true);
                debitCard.setExpireDate(expireDate);
                int cvc = new Random().nextInt(900) + 100;
                debitCard.setCvc(String.valueOf(cvc));
                DebitCard resp = service.assignCardToAccount(debitCard);
                return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(resp));
           } //eliminar
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND) //eliminar
                    .body(String.format("No se encontró cuenta con Nro: %s", debitCard.getNroAccount()))); //elimina
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage()));
        }
    }

    @GetMapping("/debitCard/{nroCard}/{nroAccount}")
    public ResponseEntity<?> findByNroCardAndNroAccount(@PathVariable String nroCard, @PathVariable String nroAccount) {
        Optional<DebitCard> resp = service.findByNroCardAndNroAccount(nroCard, nroAccount);
        if (resp.isPresent()) {
            return ResponseEntity.ok().body(resp.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }
}
