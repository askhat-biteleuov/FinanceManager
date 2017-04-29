
package ru.cbr.web;

import com.fm.internal.CurrencyData;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetCursOnDateResult" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "getCursOnDateResult"
})
@XmlRootElement(name = "GetCursOnDateResponse")
public class GetCursOnDateResponse {

    @XmlElement(name = "GetCursOnDateResult")
    protected GetCursOnDateResponse.GetCursOnDateResult getCursOnDateResult;

    /**
     * Gets the value of the getCursOnDateResult property.
     *
     * @return possible object is
     * {@link GetCursOnDateResponse.GetCursOnDateResult }
     */
    public GetCursOnDateResponse.GetCursOnDateResult getGetCursOnDateResult() {
        return getCursOnDateResult;
    }

    /**
     * Sets the value of the getCursOnDateResult property.
     *
     * @param value allowed object is
     *              {@link GetCursOnDateResponse.GetCursOnDateResult }
     */
    public void setGetCursOnDateResult(GetCursOnDateResponse.GetCursOnDateResult value) {
        this.getCursOnDateResult = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;any/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "any"
    })
    public static class GetCursOnDateResult {

        //        @XmlAnyElement(lax = true)
//        protected Object any;
//
//        /**
//         * Gets the value of the any property.
//         *
//         * @return
//         *     possible object is
//         *     {@link Object }
//         *
//         */
//        public Object getAny() {
//            return any;
//        }
//
//        /**
//         * Sets the value of the any property.
//         *
//         * @param value
//         *     allowed object is
//         *     {@link Object }
//         *
//         */
//        public void setAny(Object value) {
//            this.any = value;
//        }
        @XmlElement(name = "diffgram", namespace = "urn:schemas-microsoft-com:xml-diffgram-v1")
        protected CurrencyData any;

        public CurrencyData getAny() {
            return any;
        }

        public void setAny(CurrencyData any) {
            this.any = any;
        }
    }

}
