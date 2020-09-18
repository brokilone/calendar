package brokilone.todo.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Task
 * created by Ksenya_Ushakova at 28.08.2020
 */
@Table
@Entity
@Component
public class Task {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    @NotBlank(message = "Пожалуста, заполните это поле")
    @Length(max = 100, message = "Максимальная длина 100")
    private String shortDesc;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(length = 1000)
    @NotBlank(message = "Пожалуста, заполните это поле")
    @Length(max = 1000, message = "Максимальная длина 1000")
    private String fullDesc;

    private String fileName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Дата не может быть в прошедшем времени")
    @NotNull(message = "Пожалуста, заполните это поле")
    private LocalDate execPeriod;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public void setFullDesc(String fullDesc) {
        this.fullDesc = fullDesc;
    }

    public LocalDate getExecPeriod() {
        return execPeriod;
    }

    public void setExecPeriod(LocalDate execPeriod) {
        this.execPeriod = execPeriod;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", shortDesc='" + shortDesc + '\'' +
                ", fullDesc='" + fullDesc + '\'' +
                ", fileName='" + fileName + '\'' +
                ", execPeriod=" + execPeriod +
                ", author=" + author +
                '}';
    }
}
