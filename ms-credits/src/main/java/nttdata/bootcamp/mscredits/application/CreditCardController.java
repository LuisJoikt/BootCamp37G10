package nttdata.bootcamp.mscredits.application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nttdata.bootcamp.mscredits.interfaces.ICreditCardService;
import nttdata.bootcamp.mscredits.interfaces.ICreditService;
import nttdata.bootcamp.mscredits.model.Credit;
import nttdata.bootcamp.mscredits.model.CreditCard;
import reactor.core.publisher.Mono;

@RestController
public class CreditCardController {

    @Autowired
    private ICreditCardService service;
    @Autowired
    private ICreditService creditService;

    @GetMapping("/card/byNroCredit/{nroCredit}")
    public ResponseEntity<?> findByNroCredit(@PathVariable String nroCredit) {
        Mono<CreditCard> resp = Mono.just(service.findCardByNroCredit(nroCredit));
        return ResponseEntity.ok().body(resp);
    }

    @PostMapping("/associate_card")
    public ResponseEntity<?> associateCardToCredit(@RequestBody CreditCard card) {
        try {
            Optional<Credit> optCredit = creditService.findCreditByNroCredit(card.getNroCredit());
            if (optCredit.isPresent()) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Date expireDate = DateUtils.addYears(fmt.parse(LocalDate.now().toString()), 3);
                card.setIsEnabled(true);
                card.setExpireDate(expireDate);
                int cvc = new Random().nextInt(900) + 100;
                card.setCvc(String.valueOf(cvc));
                Mono<CreditCard> response = service.associateCardToCredit(card);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("No existe credito con Nro: %s", card.getNroCredit()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelCard(@RequestBody CreditCard card) {
        card.setIsEnabled(false);
        Mono<CreditCard> resp = service.cancelCard(card);
        return ResponseEntity.ok().body(resp);
    }

    @GetMapping("/creditCard/{nroCard}/{nroCredit}")
    public ResponseEntity<?> findByNroCardAndNroCredit(@PathVariable String nroCard, @PathVariable String nroCredit) {
        Optional<CreditCard> optCreditCard = service.findByNroCardAndNroCredit(nroCard, nroCredit);
        if (optCreditCard.isPresent()) {
            return ResponseEntity.ok().body(optCreditCard.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }
}
