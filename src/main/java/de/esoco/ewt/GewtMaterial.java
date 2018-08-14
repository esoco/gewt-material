//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2018 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
package de.esoco.ewt;

import gwt.material.design.client.base.AbstractButton;
import gwt.material.design.client.base.HasDurationTransition;
import gwt.material.design.client.base.HasErrorText;
import gwt.material.design.client.base.HasFloat;
import gwt.material.design.client.base.HasHideOn;
import gwt.material.design.client.base.HasIcon;
import gwt.material.design.client.base.HasInOutDurationTransition;
import gwt.material.design.client.base.HasOrientation;
import gwt.material.design.client.base.HasPlaceholder;
import gwt.material.design.client.base.HasTextAlign;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.ButtonSize;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconSize;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.ShowOn;
import gwt.material.design.client.constants.TextAlign;
import gwt.material.design.client.constants.WavesType;

import de.esoco.ewt.component.Button;
import de.esoco.ewt.component.CheckBox;
import de.esoco.ewt.component.Component;
import de.esoco.ewt.component.Label;
import de.esoco.ewt.component.List;
import de.esoco.ewt.component.ListBox;
import de.esoco.ewt.component.ProgressBar;
import de.esoco.ewt.component.RadioButton;
import de.esoco.ewt.component.Table;
import de.esoco.ewt.component.TextArea;
import de.esoco.ewt.component.TextField;
import de.esoco.ewt.impl.gwt.material.factory.MaterialButtonFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialCheckBoxFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialChildViewFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialLabelFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialListControlFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialProgressBarFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialRadioButtonFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialTableFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialTextAreaFactory;
import de.esoco.ewt.impl.gwt.material.factory.MaterialTextBoxFactory;
import de.esoco.ewt.impl.gwt.material.layout.MaterialLayoutFactory;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.Color;
import de.esoco.lib.property.LayoutVisibility;
import de.esoco.lib.property.Orientation;
import de.esoco.lib.property.RelativeScale;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import static gwt.material.design.client.constants.Orientation.LANDSCAPE;
import static gwt.material.design.client.constants.Orientation.PORTRAIT;

import static de.esoco.lib.property.ContentProperties.ICON;
import static de.esoco.lib.property.ContentProperties.PLACEHOLDER;
import static de.esoco.lib.property.LayoutProperties.BUTTON_SIZE;
import static de.esoco.lib.property.LayoutProperties.FLOAT;
import static de.esoco.lib.property.LayoutProperties.HORIZONTAL_ALIGN;
import static de.esoco.lib.property.LayoutProperties.ICON_ALIGN;
import static de.esoco.lib.property.LayoutProperties.ICON_SIZE;
import static de.esoco.lib.property.LayoutProperties.LAYOUT_VISIBILITY;
import static de.esoco.lib.property.LayoutProperties.TEXT_ALIGN;
import static de.esoco.lib.property.StyleProperties.ANIMATION_DURATION;
import static de.esoco.lib.property.StyleProperties.ICON_COLOR;
import static de.esoco.lib.property.StyleProperties.ORIENTATION;


/********************************************************************
 * The central management and factory class for the GEWT wrapper of the
 * GwtMaterialDesign library.
 *
 * @author eso
 */
public class GewtMaterial
{
	//~ Static fields/initializers ---------------------------------------------

	private static WavesType aDefaultWavesType = WavesType.LIGHT;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 */
	private GewtMaterial()
	{
	}

	//~ Static methods ---------------------------------------------------------

	/***************************************
	 * @see Component#applyComponentErrorState(Component, String)
	 */
	public static void applyComponentErrorState(
		Component rComponent,
		String    sMessage)
	{
		Widget rWidget = rComponent.getWidget();

		if (rWidget instanceof HasErrorText)
		{
			HasErrorText rHasError = (HasErrorText) rWidget;

			if (sMessage != null)
			{
				rHasError.setErrorText(rComponent.getContext()
									   .expandResource(sMessage));
			}
			else
			{
				rHasError.clearErrorText();
			}
		}
		else
		{
			Component.applyComponentErrorState(rComponent, sMessage);
		}
	}

	/***************************************
	 * Determines the {@link IconType} constant for a string containing the icon
	 * name to a widget. If the type cannot be matched a default will be used
	 * and a resource error will be logged (with {@link GWT#log(String)}).
	 *
	 * @param sIcon    The icon name in upper case
	 * @param rHasIcon The widget to apply the icon to
	 */
	public static void applyIconType(String sIcon, HasIcon rHasIcon)
	{
		IconType eIconType = null;

		try
		{
			eIconType = IconType.valueOf(sIcon);
		}
		catch (IllegalArgumentException e)
		{
			GWT.log("No icon type for " + sIcon);
			eIconType = IconType.HELP;
		}

		rHasIcon.setIconType(eIconType);
	}

	/***************************************
	 * Applies any alignment settings from a style data to the given widget.
	 *
	 * @param rWidget The material widget with alignment
	 * @param rStyle  The style to check for alignment
	 */
	public static void checkApplyAlignment(IsWidget rWidget, StyleData rStyle)
	{
		if (rWidget instanceof HasTextAlign)
		{
			HasTextAlign rHasTextAlign = (HasTextAlign) rWidget;
			Alignment    eAlignment    = rStyle.getProperty(TEXT_ALIGN, null);

			if (eAlignment == null)
			{
				eAlignment = rStyle.getProperty(HORIZONTAL_ALIGN, null);
			}

			if (eAlignment != null)
			{
				TextAlign eTextAlign = TextAlign.DEFAULT;

				switch (eAlignment)
				{
					case BEGIN:
						eTextAlign = TextAlign.LEFT;
						break;

					case FILL:
					case CENTER:
						eTextAlign = TextAlign.CENTER;
						break;

					case END:
						eTextAlign = TextAlign.RIGHT;
						break;
				}

				rHasTextAlign.setTextAlign(eTextAlign);
			}
		}
	}

	/***************************************
	 * Applies any button scale settings from a style data to the given button.
	 *
	 * @param rButton The button widget to apply the size to
	 * @param rStyle  The style to check for alignment
	 */
	public static void checkApplyButtonScale(
		AbstractButton rButton,
		StyleData	   rStyle)
	{
		RelativeScale eButtonSize = rStyle.getProperty(BUTTON_SIZE, null);

		if (eButtonSize == RelativeScale.LARGE)
		{
			rButton.setSize(ButtonSize.LARGE);
		}
	}

	/***************************************
	 * Applies any icon definition from a style data to the given widget.
	 *
	 * @param rWidget The material widget with icon attribute
	 * @param rStyle  The style to check for icon definitions
	 */
	public static void checkApplyIcon(IsWidget rWidget, StyleData rStyle)
	{
		if (rWidget instanceof HasIcon)
		{
			HasIcon rHasIcon = (HasIcon) rWidget;

			String		  sIcon		 = rStyle.getProperty(ICON, null);
			RelativeScale eIconSize  = rStyle.getProperty(ICON_SIZE, null);
			Color		  rIconColor = rStyle.getProperty(ICON_COLOR, null);
			Alignment     eAlignment = rStyle.getProperty(ICON_ALIGN, null);

			if (sIcon != null)
			{
				applyIconType(sIcon, rHasIcon);
			}

			if (eIconSize != null)
			{
				rHasIcon.setIconSize(IconSize.valueOf(eIconSize.name()));
			}

			if (rIconColor != null)
			{
				// set on Style because setIconColor expects color names
				rHasIcon.getIcon()
						.getElement()
						.getStyle()
						.setColor(rIconColor.toHtml());
			}

			if (eAlignment != null)
			{
				rHasIcon.setIconPosition(eAlignment != Alignment.END
										 ? IconPosition.LEFT
										 : IconPosition.RIGHT);
			}
		}
	}

	/***************************************
	 * Applies any alignment settings from a style data to the given widget.
	 *
	 * @param rWidget The material widget with alignment
	 * @param rStyle  The style to check for alignment
	 */
	public static void checkApplyLayoutVisibility(
		IsWidget  rWidget,
		StyleData rStyle)
	{
		LayoutVisibility eVisibility =
			rStyle.getProperty(LAYOUT_VISIBILITY, null);

		if (eVisibility != null)
		{
			HideOn eHideOn = null;

			switch (eVisibility)
			{
				case ALWAYS:
					eHideOn = HideOn.NONE;
					break;

				case LARGE:
					eHideOn = HideOn.HIDE_ON_MED_DOWN;
					break;

				case MEDIUM:
					((MaterialWidget) rWidget).setVisible(false);
					((MaterialWidget) rWidget).setShowOn(ShowOn.SHOW_ON_MED);
					break;

				case MEDIUM_AND_LARGE:
					eHideOn = HideOn.HIDE_ON_SMALL;
					break;

				case SMALL:
					eHideOn = HideOn.HIDE_ON_MED_UP;
					break;

				case SMALL_AND_LARGE:
					eHideOn = HideOn.HIDE_ON_MED;
					break;

				case SMALL_AND_MEDIUM:
					eHideOn = HideOn.HIDE_ON_LARGE;
					break;

				default:
					assert false : "No match for visibility " + eVisibility;
			}

			if (eHideOn != null)
			{
				((HasHideOn) rWidget).setHideOn(eHideOn);
			}
		}
	}

	/***************************************
	 * Checks whether certain styles need to be converted and applied to the
	 * widget of a GEWT component.
	 *
	 * @param rComponent The component to apply styles to
	 * @param rStyle     The style data to check for styles
	 */
	public static void checkApplyStyles(Component rComponent, StyleData rStyle)
	{
		Widget rWidget = rComponent.getWidget();

		if (rWidget instanceof HasPlaceholder &&
			rStyle.hasProperty(PLACEHOLDER))
		{
			String sPlaceholder = rStyle.getProperty(PLACEHOLDER, null);

			if (sPlaceholder == null)
			{
				sPlaceholder = "";
			}
			else
			{
				sPlaceholder = EWT.expandResource(rComponent, sPlaceholder);
			}

			((HasPlaceholder) rWidget).setPlaceholder(sPlaceholder);
		}

		checkApplyStyles(rWidget, rStyle);
	}

	/***************************************
	 * Checks whether certain styles need to be converted and applied to a
	 * material widget.
	 *
	 * @param rWidget The material widget to apply styles to
	 * @param rStyle  The style data to check for styles
	 */
	public static void checkApplyStyles(Widget rWidget, StyleData rStyle)
	{
		Alignment   eFloatAlign  = rStyle.getProperty(FLOAT, null);
		Orientation eOrientation = rStyle.getProperty(ORIENTATION, null);
		Integer     rDuration    = rStyle.getProperty(ANIMATION_DURATION, null);

		if (eOrientation != null && rWidget instanceof HasOrientation)
		{
			((HasOrientation) rWidget).setOrientation(eOrientation ==
													  Orientation.HORIZONTAL
													  ? LANDSCAPE : PORTRAIT);
		}

		if (eFloatAlign != null && rWidget instanceof HasFloat)
		{
			((HasFloat) rWidget).setFloat(eFloatAlign == Alignment.BEGIN
										  ? Float.LEFT : Float.RIGHT);
		}

		if (rDuration != null)
		{
			int nDuration = rDuration.intValue();

			if (rWidget instanceof HasDurationTransition)
			{
				((HasDurationTransition) rWidget).setDuration(nDuration);
			}
			else if (rWidget instanceof HasInOutDurationTransition)
			{
				((HasInOutDurationTransition) rWidget).setInDuration(nDuration);
				((HasInOutDurationTransition) rWidget).setOutDuration(nDuration);
			}
		}

		checkApplyLayoutVisibility(rWidget, rStyle);
		checkApplyAlignment(rWidget, rStyle);
		checkApplyIcon(rWidget, rStyle);
	}

	/***************************************
	 * Returns the default material widget animation.
	 *
	 * @return The default material widget animation type
	 */
	public static final WavesType getDefaultAnimation()
	{
		return aDefaultWavesType;
	}

	/***************************************
	 * Returns the current JQuery version.
	 *
	 * @return The JQuery version string
	 */
	public static native String getJQueryVersion() /*-{
		var jQuery = $wnd['jQuery'];
		if (typeof jQuery != 'undefined') {
			return jQuery.fn.jquery;
		}
		else {
			return 'undefined';
		}
	}-*/;

	/***************************************
	 * Initializes the library wrapper by registering the necessary factories.
	 */
	public static void init()
	{
		MaterialLayoutFactory aLayoutFactory = new MaterialLayoutFactory();

		EWT.setChildViewFactory(new MaterialChildViewFactory());
		EWT.setLayoutFactory(aLayoutFactory);
		EWT.setLayoutMapper(aLayoutFactory);

		Component.setWidgetStyleHandler((c, s) -> checkApplyStyles(c, s));
		Component.setWidgetErrorStateHandler(GewtMaterial::applyComponentErrorState);

		registerWidgetFactories();

		// register defaults for widgets not implemented by GewtMaterial
		EWT.registerDefaultWidgetFactories(false);
	}

	/***************************************
	 * Sets the default material widget animation.
	 *
	 * @param eWavesType The default material widget animation type
	 */
	public static final void setDefaultAnimation(WavesType eWavesType)
	{
		aDefaultWavesType = eWavesType;
	}

	/***************************************
	 * Initializes the GwtMaterialDesign-specific widget factories.
	 */
	private static void registerWidgetFactories()
	{
		MaterialListControlFactory aListControlFactory =
			new MaterialListControlFactory();

		EWT.registerWidgetFactory(List.class, aListControlFactory, true);
		EWT.registerWidgetFactory(ListBox.class, aListControlFactory, true);

		EWT.registerWidgetFactory(Button.class,
								  new MaterialButtonFactory<>(),
								  true);
		EWT.registerWidgetFactory(CheckBox.class,
								  new MaterialCheckBoxFactory<>(),
								  true);
		EWT.registerWidgetFactory(RadioButton.class,
								  new MaterialRadioButtonFactory(),
								  true);
		EWT.registerWidgetFactory(Label.class,
								  new MaterialLabelFactory<>(),
								  true);
		EWT.registerWidgetFactory(TextArea.class,
								  new MaterialTextAreaFactory(),
								  true);
		EWT.registerWidgetFactory(TextField.class,
								  new MaterialTextBoxFactory(),
								  true);
		EWT.registerWidgetFactory(ProgressBar.class,
								  new MaterialProgressBarFactory(),
								  true);
		EWT.registerWidgetFactory(Table.class,
								  new MaterialTableFactory(),
								  true);
	}
}
