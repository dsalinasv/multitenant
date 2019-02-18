package server.system.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class ClimData implements Serializable {

    @Id
    @Size(max = 8)
    private String fecha;
    private Double temp;
    private Double eto;  
    private Character tipo;

}
