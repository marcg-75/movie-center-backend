package se.giron.moviecenter.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.giron.moviecenter.core.service.BaseDataService;
import se.giron.moviecenter.model.entity.Format;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Role;
import se.giron.moviecenter.model.entity.Studio;

import java.util.List;

@Api(tags = "Base", value = "Api for fetching shared base data")
@RestController
@RequestMapping("/base")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    @ApiOperation(value = "Get genres")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Genre.class, responseContainer = "List")
    })
    @GetMapping(value = "/genres")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenres() {
        return baseDataService.getAllGenres();
    }

    @ApiOperation(value = "Get roles")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Role.class, responseContainer = "List")
    })
    @GetMapping("/roles")
    @ResponseStatus(HttpStatus.OK)
    public List<Role> getAllRoles() {
        return baseDataService.getAllRoles();
    }

    @ApiOperation(value = "Get formats")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Format.class, responseContainer = "List")
    })
    @GetMapping("/formats")
    @ResponseStatus(HttpStatus.OK)
    public List<Format> getAllFormats() {
        return baseDataService.getAllFormats();
    }

    @ApiOperation(value = "Get studios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Studio.class, responseContainer = "List")
    })
    @GetMapping("/studios")
    @ResponseStatus(HttpStatus.OK)
    public List<Studio> getAllStudios() {
        return baseDataService.getAllStudios();
    }
}
