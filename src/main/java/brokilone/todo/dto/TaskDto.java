package brokilone.todo.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * TaskDto
 * created by Ksenya_Ushakova at 04.09.2020
 */
public class TaskDto {
    private Long id;
    private String shortDesc;
    private String fullDesc;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate execPeriod;
    private boolean isExist;

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

    public boolean getIsExist() {
        return isExist;
    }

    public void setIsExist(boolean exist) {
        isExist = exist;
    }
}
