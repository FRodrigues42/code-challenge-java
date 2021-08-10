package pt.frodrigues.challenge.service.api.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.OffsetDateTime;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Difference
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-08-09T02:09:27.280675+01:00[Europe/Lisbon]")
public class Difference   {
  @JsonProperty("datetime")
  @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime datetime;

  @JsonProperty("value")
  private Long value;

  @JsonProperty("number")
  private Long number;

  @JsonProperty("occurrences")
  private Long occurrences;

  public Difference datetime(OffsetDateTime datetime) {
    this.datetime = datetime;
    return this;
  }

  /**
   * Get datetime
   * @return datetime
  */
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getDatetime() {
    return datetime;
  }

  public void setDatetime(OffsetDateTime datetime) {
    this.datetime = datetime;
  }

  public Difference value(Long value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  */
  @ApiModelProperty(value = "")


  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public Difference number(Long number) {
    this.number = number;
    return this;
  }

  /**
   * Get number
   * @return number
  */
  @ApiModelProperty(value = "")


  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
    this.number = number;
  }

  public Difference occurrences(Long occurrences) {
    this.occurrences = occurrences;
    return this;
  }

  /**
   * Get occurrences
   * @return occurrences
  */
  @ApiModelProperty(value = "")


  public Long getOccurrences() {
    return occurrences;
  }

  public void setOccurrences(Long occurrences) {
    this.occurrences = occurrences;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Difference difference = (Difference) o;
    return Objects.equals(this.datetime, difference.datetime) &&
        Objects.equals(this.value, difference.value) &&
        Objects.equals(this.number, difference.number) &&
        Objects.equals(this.occurrences, difference.occurrences);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datetime, value, number, occurrences);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Difference {\n");
    
    sb.append("    datetime: ").append(toIndentedString(datetime)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    number: ").append(toIndentedString(number)).append("\n");
    sb.append("    occurrences: ").append(toIndentedString(occurrences)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

