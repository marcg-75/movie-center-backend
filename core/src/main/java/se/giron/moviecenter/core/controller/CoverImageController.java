package se.giron.moviecenter.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.giron.moviecenter.core.service.CoverImageService;
import se.giron.moviecenter.model.resource.error.ErrorResponse;

import java.io.FileNotFoundException;
import java.io.IOException;

@Api(tags = "CoverImage", value = "Api for fetching cover images")
@RestController
@RequestMapping("/image")
public class CoverImageController {

    @Autowired
    private CoverImageService coverImageService;

    @ApiOperation(value = "Fetch an image by its file name")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Image not found"),
        @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(
        value = "/{fileName}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        try {
            byte[] image = coverImageService.getImage(fileName);

            return ResponseEntity.ok(image);
        } catch (FileNotFoundException fnfe) {
            return ResponseEntity.notFound().build();
        }
    }
}
