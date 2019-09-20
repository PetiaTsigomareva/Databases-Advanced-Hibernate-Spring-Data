package mostwanted.domain.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryRootDto {
    @XmlElement(name = "entry")
    private List<EntryDto> etriEntryDtoList;

    public EntryRootDto() {
        this.etriEntryDtoList = new ArrayList<>();
    }

    public List<EntryDto> getEtriEntryDtoList() {
        return etriEntryDtoList;
    }

    public void setEtriEntryDtoList(List<EntryDto> etriEntryDtoList) {
        this.etriEntryDtoList = etriEntryDtoList;
    }
}
