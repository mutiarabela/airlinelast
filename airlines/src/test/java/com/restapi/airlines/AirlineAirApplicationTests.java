package com.restapi.airlines;

import com.restapi.airlines.controller.AirlineController;
import com.restapi.airlines.exception.AirlineNotFoundException;
import com.restapi.airlines.model.request.AirlineDetailsRequestModel;
import com.restapi.airlines.model.response.AirlineResponseModel;
import com.restapi.airlines.model.response.ErrorMessage;
import com.restapi.airlines.service.AirlineService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AirlineAirApplicationTests {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    AirlineService airlineService;

    @Autowired
    AirlineController airlineController;

    @Test
    public void createAirline(){
        AirlineDetailsRequestModel airline = new AirlineDetailsRequestModel();
        airline.setIdAirline("1");
        airline.setNameAirline("Garuda");
        airline.setTypeAirline("Airplane");
        airline.setAirportOriginAirline("Soekarno Hatta");
        airline.setAirportDestinationAirline("Adi Soetjipto");
        airline.setDestinationAirline("Yogyakarta");
        airline.setPriceAirline(1250000.00);
        airline.setDiscountAirline(10);

        AirlineResponseModel create = airlineService.createAirline(airline);

        assertThat(create.getIdAirline()).isEqualTo(airline.getIdAirline());
        assertThat(create.getNameAirline()).isEqualTo(airline.getNameAirline());
        assertThat(create.getTypeAirline()).isEqualTo(airline.getTypeAirline());
        assertThat(create.getAirportOriginAirline()).isEqualTo(airline.getAirportOriginAirline());
        assertThat(create.getAirportDestinationAirline()).isEqualTo(airline.getAirportDestinationAirline());
        assertThat(create.getDestinationAirline()).isEqualTo(airline.getDestinationAirline());
        assertThat(create.getPriceAirline()).isEqualTo(airline.getPriceAirline());
        assertThat(create.getDiscountAirline()).isEqualTo(airline.getDiscountAirline());

        ResponseEntity createAirline = airlineController.createAirline(airline);
        assertThat(createAirline.getStatusCode().is2xxSuccessful()).isEqualTo(true);
    }

    @Test
    public void getAirline() {

        createAirline();

        String idAirline  = "1";

        AirlineResponseModel result = airlineService.getAirline(idAirline);
        assertThat(result.getIdAirline()).isEqualTo("1");
        assertThat(result.getNameAirline()).isEqualTo("Garuda");
        assertThat(result.getTypeAirline()).isEqualTo("Airplane");
        assertThat(result.getAirportOriginAirline()).isEqualTo("Soekarno Hatta");
        assertThat(result.getAirportDestinationAirline()).isEqualTo("Adi Soetjipto");
        assertThat(result.getDestinationAirline()).isEqualTo("Yogyakarta");
        assertThat(result.getPriceAirline()).isEqualTo(1250000.00);
        assertThat(result.getDiscountAirline()).isEqualTo(10);

        ResponseEntity responseEntity = airlineController.getAirline(idAirline);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isEqualTo(true);
    }

    @Test
    public void getAllAirline(){
        createAirline();

        Collection getCreatedAirlines = airlineService.getAllAirline();
        assertThat(getCreatedAirlines.size()).isEqualTo(3);

        ResponseEntity getAllAirline = airlineController.getAllAirline();
        assertThat(getAllAirline.getStatusCode().is2xxSuccessful()).isEqualTo(true);
    }

    @Test
    public void updatePriceAirline(){
        createAirline();

        log.info("Update priceAirline=1150000.00 for idAirline=1");

        String idAirline = "1";
        AirlineResponseModel airlineResponseModel = airlineService.getAirline(idAirline);
        String updatedIdAirline = airlineResponseModel.getIdAirline();
        double updatedPriceAirline = airlineResponseModel.getPriceAirline();

        AirlineDetailsRequestModel airlineDetailsRequestModel = new AirlineDetailsRequestModel();
        AirlineResponseModel updatedAirline = airlineService.updatePriceAirline(idAirline, airlineDetailsRequestModel);

        updatedAirline.setPriceAirline(1150000.00);

        assertThat(updatedAirline.getIdAirline()).isEqualTo(updatedIdAirline);
        assertThat(updatedAirline.getPriceAirline()).isNotEqualTo(updatedPriceAirline);

        ResponseEntity updatePriceAirline = airlineController.updatePriceAirline(idAirline, airlineDetailsRequestModel);
        assertThat(updatePriceAirline.getStatusCode().is2xxSuccessful()).isEqualTo(true);
    }

    @Test
    public void updatePriceAirlineErr(){
        exceptionRule.expect(AirlineNotFoundException.class);
        exceptionRule.expectMessage("Update Airline Failed, Airline Not Found");
        createAirline();

        log.info("Update priceAirline=1150000.00 for idAirline=1");

        String idAirline = "1";
        AirlineResponseModel airlineResponseModel = airlineService.getAirline(idAirline);
        String updatedIdAirline = airlineResponseModel.getIdAirline();
        double updatedPriceAirline = airlineResponseModel.getPriceAirline();

        AirlineDetailsRequestModel airlineDetailsRequestModel = new AirlineDetailsRequestModel();
        AirlineResponseModel updatedAirline = airlineService.updatePriceAirline(idAirline, airlineDetailsRequestModel);

        String idAirlineErr = "10";
        updatedAirline.setPriceAirline(1150000.00);

        ResponseEntity updatePriceAirline = airlineController.updatePriceAirline(idAirlineErr, airlineDetailsRequestModel);
    }

    @Test
    public void deleteAirline(){
        createAirline();

        AirlineResponseModel getCreatedAirline = airlineService.getAirline("1");
        String idAirline = getCreatedAirline.getIdAirline();

        airlineService.deleteAirline(idAirline);
        assertThat(airlineService.getAirline(idAirline)).isNull();

    }

    @Test
    public void deleteAirlineCont(){
        createAirline();

        AirlineResponseModel getCreatedAirline = airlineService.getAirline("1");
        String idAirline = getCreatedAirline.getIdAirline();

        ResponseEntity deleteAirline = airlineController.deleteAirline("1");
        assertThat(deleteAirline.getStatusCode().is2xxSuccessful()).isEqualTo(true);

    }

    @Test(expected = AirlineNotFoundException.class)
    public void airlineNotFoundExceptionGet(){
        createAirline();
        String idAirline = "5";
        exceptionRule.equals(ErrorMessage.builder()
                     .status(HttpStatus.BAD_REQUEST
                     .value()).message("Airline ID you sent is invalid / not match")
        );
        ResponseEntity get = airlineController.getAirline(idAirline);
        log.info(get.toString());

    }

    @Test
    public void deleteNotFound(){
        exceptionRule.expect(AirlineNotFoundException.class);
        exceptionRule.expectMessage("Delete Airline Failed, Airline Not Found");

        String idAirline = "9";

        ResponseEntity deleteAirline = airlineController.deleteAirline(idAirline);
    }
//
//    @Test
//    public void createAirlineErr() throws AirlineNotFoundException{
//        try {
//            AirlineDetailsRequestModel airline = new AirlineDetailsRequestModel();
//            airline.setIdAirline("");
//            airline.setNameAirline("Garuda");
//            airline.setTypeAirline("Airplane");
//            airline.setAirportOriginAirline("Soekarno Hatta");
//            airline.setAirportDestinationAirline("Adi Soetjipto");
//            airline.setDestinationAirline("Yogyakarta");
//            airline.setPriceAirline(1250000.00);
//            airline.setDiscountAirline(10);
//
//            ResponseEntity create = airlineController.createAirline(airline);
//        }catch (AirlineNotFoundException e){
//            log.error("error nih");
//        }
//    }

}
