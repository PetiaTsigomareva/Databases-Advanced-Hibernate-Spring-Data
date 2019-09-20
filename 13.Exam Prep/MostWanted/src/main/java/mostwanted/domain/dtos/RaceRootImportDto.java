package mostwanted.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceRootImportDto {
    @XmlElement(name = "race")
    private List<RaceImportDto> raceImportDtos;

    public RaceRootImportDto() {
        this.raceImportDtos = new ArrayList<>();
    }

    public List<RaceImportDto> getRaceImportDtos() {
        return raceImportDtos;
    }

    public void setRaceImportDtos(List<RaceImportDto> raceImportDtos) {
        this.raceImportDtos = raceImportDtos;
    }
}
