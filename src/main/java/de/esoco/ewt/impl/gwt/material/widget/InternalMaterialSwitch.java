//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.base.HasError;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.mixin.ErrorMixin;
import gwt.material.design.client.constants.InputType;
import gwt.material.design.client.ui.MaterialInput;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.html.Span;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;


/********************************************************************
 * Contains a workaround for a material switch event handling bug:
 * https://github.com/GwtMaterialDesign/gwt-material/issues/389
 *
 * @author eso
 */
@SuppressWarnings("boxing")
class InternalMaterialSwitch extends MaterialWidget
	implements HasValue<Boolean>, HasClickHandlers, HasError
{
	//~ Instance fields --------------------------------------------------------

	private MaterialInput input    = new MaterialInput();
	private Span		  span     = new Span();
	private Label		  label    = new Label();
	private MaterialLabel lblError = new MaterialLabel();
	private Span		  onLabel  = new Span();
	private Span		  offLabel = new Span();

	private final ErrorMixin<InternalMaterialSwitch, MaterialLabel> errorMixin =
		new ErrorMixin<>(this, lblError, null);

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a switch element
	 */
	public InternalMaterialSwitch()
	{
		super(Document.get().createDivElement(), "switch");

		span.setStyleName("lever");
		input.setType(InputType.CHECKBOX);

		// BF: moved from onLoad()
		addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					setValue(!getValue());
					event.preventDefault();
					event.stopPropagation();
				}
			});
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
	 */
	@Override
	public HandlerRegistration addClickHandler(final ClickHandler rHandler)
	{
		return addDomHandler(rHandler, ClickEvent.getType());
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @param  rHandler TODO: DOCUMENT ME!
	 *
	 * @return TODO: DOCUMENT ME!
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(
		final ValueChangeHandler<Boolean> rHandler)
	{
		return addHandler(rHandler, ValueChangeEvent.getType());
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasError#clearErrorOrSuccess()
	 */
	@Override
	public void clearErrorOrSuccess()
	{
		errorMixin.clearErrorOrSuccess();
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @return the input
	 */
	public MaterialInput getInput()
	{
		return input;
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @return the label
	 */
	@Deprecated
	public Label getLabel()
	{
		return label;
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @return the span
	 */
	public Span getSpan()
	{
		return span;
	}

	/***************************************
	 * Gets the value of switch component.
	 *
	 * @return TODO: DOCUMENT ME!
	 */
	@Override
	public Boolean getValue()
	{
		return input.getElement().hasAttribute("checked");
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		getElement().setAttribute("style",
								  "background-color:transparent !important");
		label.getElement()
			 .setAttribute("style", "background-color:transparent !important");
		span.setEnabled(enabled);
		input.setEnabled(enabled);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasError#setError(java.lang.String)
	 */
	@Override
	public void setError(String error)
	{
		errorMixin.setError(error);
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasError#setHelperText(java.lang.String)
	 */
	@Override
	public void setHelperText(String sHelperText)
	{
		errorMixin.setHelperText(sHelperText);
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @param input the input to set
	 */
	public void setInput(MaterialInput input)
	{
		this.input = input;
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @param label the label to set
	 */
	@Deprecated
	public void setLabel(Label label)
	{
		this.label = label;
	}

	/***************************************
	 * Set the Off State Label of the switch component
	 *
	 * @param label TODO: DOCUMENT ME!
	 */
	public void setOffLabel(String label)
	{
		offLabel.setText(label);
	}

	/***************************************
	 * Set the On State Label of the switch component
	 *
	 * @param label TODO: DOCUMENT ME!
	 */
	public void setOnLabel(String label)
	{
		onLabel.setText(label);
	}

	/***************************************
	 * TODO: DOCUMENT ME!
	 *
	 * @param span the span to set
	 */
	public void setSpan(Span span)
	{
		this.span = span;
	}

	/***************************************
	 * @see gwt.material.design.client.base.HasError#setSuccess(java.lang.String)
	 */
	@Override
	public void setSuccess(String success)
	{
		errorMixin.setSuccess(success);
	}

	/***************************************
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Boolean)
	 */
	@Override
	public void setValue(Boolean value)
	{
		setValue(value, true);
	}

	/***************************************
	 * Set the value of switch component.
	 *
	 * @param value      TODO: DOCUMENT ME!
	 * @param fireEvents TODO: DOCUMENT ME!
	 */
	@Override
	public void setValue(Boolean value, boolean fireEvents)
	{
		boolean oldValue = getValue();

		if (value)
		{
			input.getElement().setAttribute("checked", "true");
		}
		else
		{
			input.getElement().removeAttribute("checked");
		}

		if (fireEvents && oldValue != value)
		{
			ValueChangeEvent.fire(this, getValue());
		}
	}

	/***************************************
	 * @see gwt.material.design.client.base.MaterialWidget#onLoad()
	 */
	@Override
	protected void onLoad()
	{
		super.onLoad();

		label.add(offLabel);
		label.add(input);
		label.add(span);
		add(label);
		add(lblError);
		lblError.getElement().getStyle().setMarginTop(16, Unit.PX);
		label.add(onLabel);
	}
}
