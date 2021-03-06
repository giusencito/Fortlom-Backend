package upc.edu.pe.FortlomBackend.backend.domain.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Type;
import upc.edu.pe.FortlomBackend.shared.domain.model.AuditModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@With
@Table(name = "Fanatic")

public class Fanatic  {

    @Id
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(unique = true)
    private String alias;

    @OneToMany(targetEntity = Follow.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "fanaticid",referencedColumnName = "id")
    private List<Follow> follow;

    @OneToMany(targetEntity = Rate.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "fanaticid",referencedColumnName = "id")
    private List<Rate> rate;

}
