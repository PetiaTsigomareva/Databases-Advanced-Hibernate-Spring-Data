package mostwanted.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryRootImportDto {
    @XmlElement(name = "race-entry")
    private List<RaceEntryImportDto> raceEntryImportDtos;

    public RaceEntryRootImportDto() {
        this.raceEntryImportDtos = new ArrayList<>();
    }

    public List<RaceEntryImportDto> getRaceEntryImportDtos() {
        return raceEntryImportDtos;
    }

    public void setRaceEntryImportDtos(List<RaceEntryImportDto> raceEntryImportDtos) {
        this.raceEntryImportDtos = raceEntryImportDtos;
    }
}
