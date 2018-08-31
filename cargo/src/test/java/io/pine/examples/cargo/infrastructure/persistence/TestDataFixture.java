package io.pine.examples.cargo.infrastructure.persistence;

import io.pine.examples.cargo.domain.model.cargo.Cargo;
import io.pine.examples.cargo.domain.model.cargo.RouteSpecification;
import io.pine.examples.cargo.domain.model.cargo.TrackingId;
import io.pine.examples.cargo.domain.model.location.Location;
import io.pine.examples.cargo.domain.model.location.SampleLocations;
import io.pine.examples.cargo.domain.model.voyage.Voyage;
import io.pine.examples.cargo.domain.model.voyage.VoyageNumber;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Frank
 * @sinace 2018/8/29 0029.
 */
public class TestDataFixture {
    private static final Timestamp base;

    static {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2008-01-01");
            base = new Timestamp(date.getTime() - 1000L * 60 * 60 * 24 * 100);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static Timestamp ts(int hours) {
        return new Timestamp(base.getTime() + 1000L * 60 * 60 * hours);
    }

    public static Date offset(int hours) {
        return new Date(ts(hours).getTime());
    }

    public final static List<Location> buildLocationData() {
        return Arrays.asList(
                SampleLocations.STOCKHOLM,
                SampleLocations.MELBOURNE,
                SampleLocations.HONGKONG,
                SampleLocations.TOKYO,
                SampleLocations.HELSINKI,
                SampleLocations.HAMBURG,
                SampleLocations.CHICAGO
        );
    }

    public final static List<Voyage> buildCarrierMovementData() {
        // SESTO - FIHEL - DEHAM - CNHKG - JPTOK - AUMEL (voyage 0101)
        Voyage voyage0101 = new Voyage.Builder(new VoyageNumber("0101"), SampleLocations.STOCKHOLM)
                .addMovement(SampleLocations.HELSINKI, ts(1), ts(2))
                .addMovement(SampleLocations.HAMBURG, ts(1), ts(2))
                .addMovement(SampleLocations.HONGKONG, ts(1), ts(2))
                .addMovement(SampleLocations.TOKYO, ts(1), ts(2))
                .addMovement(SampleLocations.MELBOURNE, ts(1), ts(2))
                .build();

        // AUMEL - USCHI - DEHAM - SESTO - FIHEL (voyage 0202)
        Voyage voyage0202 = new Voyage.Builder(new VoyageNumber("0202"), SampleLocations.MELBOURNE)
                .addMovement(SampleLocations.CHICAGO, ts(1), ts(2))
                .addMovement(SampleLocations.HAMBURG, ts(1), ts(2))
                .addMovement(SampleLocations.STOCKHOLM, ts(1), ts(2))
                .addMovement(SampleLocations.HELSINKI, ts(1), ts(2))
                .build();

        // CNHKG - AUMEL - FIHEL - DEHAM - SESTO - USCHI - JPTKO (voyage 0303)
        Voyage voyage0303 = new Voyage.Builder(new VoyageNumber("0202"), SampleLocations.HONGKONG)
                .addMovement(SampleLocations.MELBOURNE, ts(1), ts(2))
                .addMovement(SampleLocations.HELSINKI, ts(1), ts(2))
                .addMovement(SampleLocations.HAMBURG, ts(1), ts(2))
                .addMovement(SampleLocations.STOCKHOLM, ts(1), ts(2))
                .addMovement(SampleLocations.CHICAGO, ts(1), ts(2))
                .addMovement(SampleLocations.TOKYO, ts(1), ts(2))
                .build();

        return Arrays.asList(voyage0101, voyage0202, voyage0303);
    }

    public final static List<Cargo> buildCargoData() {
        Cargo cargoXyz = new Cargo(new TrackingId("XYZ"), new RouteSpecification(SampleLocations.STOCKHOLM, SampleLocations.MELBOURNE, ts(10)));
        Cargo cargoZyx = new Cargo(new TrackingId("ZYX"), new RouteSpecification(SampleLocations.MELBOURNE, SampleLocations.STOCKHOLM, ts(10)));
        Cargo cargoAbc = new Cargo(new TrackingId("ABC"), new RouteSpecification(SampleLocations.STOCKHOLM, SampleLocations.HELSINKI, ts(10)));
        Cargo cargoCba = new Cargo(new TrackingId("CBA"), new RouteSpecification(SampleLocations.HELSINKI, SampleLocations.STOCKHOLM, ts(10)));
        return Arrays.asList(cargoXyz, cargoZyx, cargoAbc, cargoCba);
    }

}
