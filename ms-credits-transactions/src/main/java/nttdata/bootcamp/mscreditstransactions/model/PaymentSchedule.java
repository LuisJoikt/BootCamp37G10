package nttdata.bootcamp.mscreditstransactions.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class PaymentSchedule {
    @Id
    private String id;
    private String nroCredit;
    private String idTransaction;
    // MONTO DE LA CUOTA
    private Double monthlyFee;
    // FECHA DE VENCIMIENTO DE LA CUOTA
    private Date payDateFee;
    // STATO DE PAGO DE CUOTA
    private String statePayFee;
}
