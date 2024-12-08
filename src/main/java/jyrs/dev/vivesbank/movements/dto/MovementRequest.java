package jyrs.dev.vivesbank.movements.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* Clase que representa la solicitud de creacion de un movimiento.
* @author Raul Fernandez, Yahya El Hadri, Javier Ruiz, Javier Hernandez, Samuel cortes.
* @since 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovementRequest {

    private String bankAccountOrigin;

    private String clientRecipientId;

    private String bankAccountDestination;

    private Double amount;

    private String typeMovement;
}