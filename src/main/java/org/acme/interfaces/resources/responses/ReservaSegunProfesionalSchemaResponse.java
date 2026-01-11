
package org.acme.interfaces.resources.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numeroReserva",
        "cliente",
        "profesional"
})
@Generated("jsonschema2pojo")
public class ReservaSegunProfesionalSchemaResponse {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("numeroReserva")
    private String numeroReserva;
    /**
     *
     * (Required)
     *
     */
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cliente")
    private String cliente;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesional")
    private String profesional;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("numeroReserva")
    public String getNumeroReserva() {
        return numeroReserva;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("numeroReserva")
    public void setNumeroReserva(String numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cliente")
    public String getCliente() {
        return cliente;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cliente")
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesional")
    public String getProfesional() {
        return profesional;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesional")
    public void setProfesional(String profesional) {
        this.profesional = profesional;
    }

}
