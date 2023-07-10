package com.spotify.oauth2.pojo.PlayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.oauth2.utils.FakerUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PlayList {

    @JsonProperty("name")
    private String name = Stream.of("Happy", "Sad", "Rock", "Old", "New").findAny().get();

    @JsonProperty("description")
    private String description = FakerUtils.generateDescription();

    @JsonProperty("public")
    private Boolean _public = FakerUtils.generateRandomBoolean();
}
