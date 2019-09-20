package app.exam.domain.dto.json;

import javax.persistence.ElementCollection;
import java.util.List;

public class ItemJSONExportDTO {

    private  String employeeName;
    private List<OrderJSONExportDTO> orders;
}
