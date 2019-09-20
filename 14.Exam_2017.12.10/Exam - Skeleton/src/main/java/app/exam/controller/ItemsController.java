package app.exam.controller;

import app.exam.domain.dto.json.ItemJSONImportDTO;
import app.exam.service.api.ItemsService;
import app.exam.util.ValidationUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ItemsController {
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ItemsService itemsService;

    @Autowired
    public ItemsController(Gson gson, ValidationUtil validationUtil, ItemsService itemsService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.itemsService = itemsService;
    }


    public String importDataFromJSON(String jsonContent) {
        StringBuilder importResult = new StringBuilder();
        ItemJSONImportDTO[] itemJSONImportDTOS = this.gson.fromJson(jsonContent, ItemJSONImportDTO[].class);

        for (ItemJSONImportDTO itemJSONImportDTO : itemJSONImportDTOS) {
            if (!this.validationUtil.isValid(itemJSONImportDTO)) {
                importResult.append("Error: Invalid data.").append(System.lineSeparator());
                continue;
            }
            this.itemsService.create(itemJSONImportDTO);
            importResult.append(String.format("Successfully imported item - %s", itemJSONImportDTO.getName())).append(System.lineSeparator());
        }

        return importResult.toString().trim();
    }
}
