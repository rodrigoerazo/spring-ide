//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="https://java.sun.com/xml/jaxb">https://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 05:47:27 PM CET 
//

package org.springframework.ide.eclipse.osgi.blueprint.internal.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.dom.Element;

/**
 * 
 * 
 * A registration listener definition. The target registration listener can be
 * either a <ref> to a <bean> or <service> component, or an inline <bean> or
 * <service> component definition. The registration-method and
 * unregistration-method attributes define the methods that will be called for
 * the respective events.
 * 
 * For the very common case of using a <ref> to a listener component, the ref
 * attribute may also be used as a shortcut.
 * 
 * 
 * 
 * <p>
 * Java class for TregistrationListener complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="TregistrationListener">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.osgi.org/xmlns/blueprint/v1.0.0}GtargetComponent" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ref" type="{http://www.osgi.org/xmlns/blueprint/v1.0.0}Tidref" />
 *       &lt;attribute name="registration-method" type="{http://www.osgi.org/xmlns/blueprint/v1.0.0}Tmethod" />
 *       &lt;attribute name="unregistration-method" type="{http://www.osgi.org/xmlns/blueprint/v1.0.0}Tmethod" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TregistrationListener", propOrder = { "bean", "reference", "targetRef", "any" })
public class TregistrationListener {

	protected TinlinedBean bean;
	protected TinlinedReference reference;
	@XmlElement(name = "ref")
	protected Tref targetRef;
	@XmlAnyElement(lax = true)
	protected Object any;
	@XmlAttribute(name = "ref")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String ref;
	@XmlAttribute(name = "registration-method")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String registrationMethod;
	@XmlAttribute(name = "unregistration-method")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	protected String unregistrationMethod;

	/**
	 * Gets the value of the bean property.
	 * 
	 * @return possible object is {@link TinlinedBean }
	 * 
	 */
	public TinlinedBean getBean() {
		return bean;
	}

	/**
	 * Sets the value of the bean property.
	 * 
	 * @param value
	 *            allowed object is {@link TinlinedBean }
	 * 
	 */
	public void setBean(TinlinedBean value) {
		this.bean = value;
	}

	/**
	 * Gets the value of the reference property.
	 * 
	 * @return possible object is {@link TinlinedReference }
	 * 
	 */
	public TinlinedReference getReference() {
		return reference;
	}

	/**
	 * Sets the value of the reference property.
	 * 
	 * @param value
	 *            allowed object is {@link TinlinedReference }
	 * 
	 */
	public void setReference(TinlinedReference value) {
		this.reference = value;
	}

	/**
	 * Gets the value of the targetRef property.
	 * 
	 * @return possible object is {@link Tref }
	 * 
	 */
	public Tref getTargetRef() {
		return targetRef;
	}

	/**
	 * Sets the value of the targetRef property.
	 * 
	 * @param value
	 *            allowed object is {@link Tref }
	 * 
	 */
	public void setTargetRef(Tref value) {
		this.targetRef = value;
	}

	/**
	 * Gets the value of the any property.
	 * 
	 * @return possible object is {@link Element } {@link Object }
	 * 
	 */
	public Object getAny() {
		return any;
	}

	/**
	 * Sets the value of the any property.
	 * 
	 * @param value
	 *            allowed object is {@link Element } {@link Object }
	 * 
	 */
	public void setAny(Object value) {
		this.any = value;
	}

	/**
	 * Gets the value of the ref property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * Sets the value of the ref property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRef(String value) {
		this.ref = value;
	}

	/**
	 * Gets the value of the registrationMethod property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRegistrationMethod() {
		return registrationMethod;
	}

	/**
	 * Sets the value of the registrationMethod property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRegistrationMethod(String value) {
		this.registrationMethod = value;
	}

	/**
	 * Gets the value of the unregistrationMethod property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUnregistrationMethod() {
		return unregistrationMethod;
	}

	/**
	 * Sets the value of the unregistrationMethod property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUnregistrationMethod(String value) {
		this.unregistrationMethod = value;
	}

}
