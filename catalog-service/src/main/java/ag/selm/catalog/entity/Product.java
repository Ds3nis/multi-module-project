package ag.selm.catalog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(schema = "catalog", name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "c_title", nullable = false)
    @NotNull
    @Size(min = 3, max = 50)
    private String title;

    @Column(name = "c_details", nullable = false)
    @NotNull
    @Size(max = 1000)
    private String details;

}

