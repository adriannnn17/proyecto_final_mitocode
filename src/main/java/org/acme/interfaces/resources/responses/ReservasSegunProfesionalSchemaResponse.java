
package org.acme.interfaces.resources.responses;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
@Generated("jsonschema2pojo")
public class ReservasSegunProfesionalSchemaResponse {

    @JsonIgnore
    private Map<String, List<ReservaSegunProfesionalSchemaResponse>> additionalProperties = new LinkedHashMap<String, List<ReservaSegunProfesionalSchemaResponse>>();

    @JsonAnyGetter
    public Map<String, List<ReservaSegunProfesionalSchemaResponse>> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(Map<String, List<ReservaSegunProfesionalSchemaResponse>> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}
