package models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeRateResponseBody {
    private int statusCode;
    @JsonProperty("result")
    private String result;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("documentation")
    private String documentation;
    @JsonProperty("terms_of_use")
    private String termsOfUse;
    @JsonProperty("time_last_update_unix")
    private long timeLastUpdateUnix;
    @JsonProperty("time_last_update_utc")
    private String timeLastUpdateUtc;
    @JsonProperty("time_next_update_unix")
    private long timeNextUpdateUnix;
    @JsonProperty("time_next_update_utc")
    private String timeNextUpdateUtc;
    @JsonProperty("time_eol_unix") // This field will be ignored during deserialization
    private long timeEolUnix;
    @JsonProperty("base_code")
    private String baseCode;
    @JsonProperty("rates")
    private RatesResponseBody ratesResponseBody;
}
