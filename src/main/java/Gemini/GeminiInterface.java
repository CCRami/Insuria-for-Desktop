package Gemini;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import static Gemini.GeminiRecords.*;
@ComponentScan(basePackages = {"Gemini"})
@RestController
public interface GeminiInterface {

    @GetMapping("/v1beta/models/")
    ModelList getModels();

    @PostMapping("/{model}/countTokens")
    GeminiCountResponse countTokens(
            @PathVariable String model,
            @RequestBody GeminiRequest request);

    @PostMapping("/{model}/generateContent")
    GeminiResponse getCompletion(
            @PathVariable String model,
            @RequestBody GeminiRequest request);
}
