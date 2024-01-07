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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
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
import static gwt.material.design.client.constants.Orientation.LANDSCAPE;
import static gwt.material.design.client.constants.Orientation.PORTRAIT;

/**
 * The central management and factory class for the GEWT wrapper of the
 * GwtMaterialDesign library.
 *
 * @author eso
 */
public class GewtMaterial {

	private static WavesType defaultWavesType = WavesType.LIGHT;

	/**
	 * Creates a new instance.
	 */
	private GewtMaterial() {
	}

	/**
	 * @see Component#applyComponentErrorState(Component, String)
	 */
	public static void applyComponentErrorState(Component component,
		String message) {
		Widget widget = component.getWidget();

		if (widget instanceof HasErrorText) {
			HasErrorText hasError = (HasErrorText) widget;

			if (message != null) {
				hasError.setErrorText(
					component.getContext().expandResource(message));
			} else {
				hasError.clearErrorText();
			}
		} else {
			Component.applyComponentErrorState(component, message);
		}
	}

	/**
	 * Determines the {@link IconType} constant for a string containing the
	 * icon
	 * name to a widget. If the type cannot be matched a default will be used
	 * and a resource error will be logged (with {@link GWT#log(String)}).
	 *
	 * @param icon    The icon name in upper case
	 * @param hasIcon The widget to apply the icon to
	 */
	public static void applyIconType(String icon, HasIcon hasIcon) {
		IconType iconType;

		try {
			iconType = IconType.valueOf(icon);
		} catch (IllegalArgumentException e) {
			GWT.log("No icon type for " + icon);
			iconType = IconType.HELP;
		}

		hasIcon.setIconType(iconType);
	}

	/**
	 * Applies any alignment settings from a style data to the given widget.
	 *
	 * @param widget The material widget with alignment
	 * @param style  The style to check for alignment
	 */
	public static void checkApplyAlignment(IsWidget widget, StyleData style) {
		if (widget instanceof HasTextAlign) {
			HasTextAlign hasTextAlign = (HasTextAlign) widget;
			Alignment alignment = style.getProperty(TEXT_ALIGN, null);

			if (alignment == null) {
				alignment = style.getProperty(HORIZONTAL_ALIGN, null);
			}

			if (alignment != null) {
				TextAlign textAlign = TextAlign.DEFAULT;

				switch (alignment) {
					case BEGIN:
						textAlign = TextAlign.LEFT;
						break;

					case FILL:
					case CENTER:
						textAlign = TextAlign.CENTER;
						break;

					case END:
						textAlign = TextAlign.RIGHT;
						break;
				}

				hasTextAlign.setTextAlign(textAlign);
			}
		}
	}

	/**
	 * Applies any button scale settings from a style data to the given button.
	 *
	 * @param button The button widget to apply the size to
	 * @param style  The style to check for alignment
	 */
	public static void checkApplyButtonScale(AbstractButton button,
		StyleData style) {
		RelativeScale buttonSize = style.getProperty(BUTTON_SIZE, null);

		if (buttonSize == RelativeScale.LARGE) {
			button.setSize(ButtonSize.LARGE);
		}
	}

	/**
	 * Applies any icon definition from a style data to the given widget.
	 *
	 * @param widget The material widget with icon attribute
	 * @param style  The style to check for icon definitions
	 */
	public static void checkApplyIcon(IsWidget widget, StyleData style) {
		if (widget instanceof HasIcon) {
			HasIcon hasIcon = (HasIcon) widget;

			String icon = style.getProperty(ICON, null);
			RelativeScale iconSize = style.getProperty(ICON_SIZE, null);
			Color iconColor = style.getProperty(ICON_COLOR, null);
			Alignment alignment = style.getProperty(ICON_ALIGN, null);

			if (icon != null) {
				applyIconType(icon, hasIcon);
			}

			if (iconSize != null) {
				hasIcon.setIconSize(IconSize.valueOf(iconSize.name()));
			}

			if (iconColor != null) {
				// set on Style because setIconColor expects color names
				hasIcon
					.getIcon()
					.getElement()
					.getStyle()
					.setColor(iconColor.toHtml());
			}

			if (alignment != null) {
				hasIcon.setIconPosition(alignment != Alignment.END ?
				                        IconPosition.LEFT :
				                        IconPosition.RIGHT);
			}
		}
	}

	/**
	 * Applies any alignment settings from a style data to the given widget.
	 *
	 * @param widget The material widget with alignment
	 * @param style  The style to check for alignment
	 */
	public static void checkApplyLayoutVisibility(IsWidget widget,
		StyleData style) {
		LayoutVisibility visibility =
			style.getProperty(LAYOUT_VISIBILITY, null);

		if (visibility != null) {
			HideOn hideOn = null;

			switch (visibility) {
				case ALWAYS:
					hideOn = HideOn.NONE;
					break;

				case LARGE:
					hideOn = HideOn.HIDE_ON_MED_DOWN;
					break;

				case MEDIUM:
					((MaterialWidget) widget).setVisible(false);
					((MaterialWidget) widget).setShowOn(ShowOn.SHOW_ON_MED);
					break;

				case MEDIUM_AND_LARGE:
					hideOn = HideOn.HIDE_ON_SMALL;
					break;

				case SMALL:
					hideOn = HideOn.HIDE_ON_MED_UP;
					break;

				case SMALL_AND_LARGE:
					hideOn = HideOn.HIDE_ON_MED;
					break;

				case SMALL_AND_MEDIUM:
					hideOn = HideOn.HIDE_ON_LARGE;
					break;

				default:
					assert false : "No match for visibility " + visibility;
			}

			if (hideOn != null) {
				((HasHideOn) widget).setHideOn(hideOn);
			}
		}
	}

	/**
	 * Checks whether certain styles need to be converted and applied to the
	 * widget of a GEWT component.
	 *
	 * @param component The component to apply styles to
	 * @param style     The style data to check for styles
	 */
	public static void checkApplyStyles(Component component, StyleData style) {
		Widget widget = component.getWidget();

		if (widget instanceof HasPlaceholder &&
			style.hasProperty(PLACEHOLDER)) {
			String placeholder = style.getProperty(PLACEHOLDER, null);

			if (placeholder == null) {
				placeholder = "";
			} else {
				placeholder = EWT.expandResource(component, placeholder);
			}

			((HasPlaceholder) widget).setPlaceholder(placeholder);
		}

		checkApplyStyles(widget, style);
	}

	/**
	 * Checks whether certain styles need to be converted and applied to a
	 * material widget.
	 *
	 * @param widget The material widget to apply styles to
	 * @param style  The style data to check for styles
	 */
	public static void checkApplyStyles(Widget widget, StyleData style) {
		Alignment floatAlign = style.getProperty(FLOAT, null);
		Orientation orientation = style.getProperty(ORIENTATION, null);
		Integer duration = style.getProperty(ANIMATION_DURATION, null);

		if (orientation != null && widget instanceof HasOrientation) {
			((HasOrientation) widget).setOrientation(
				orientation == Orientation.HORIZONTAL ? LANDSCAPE : PORTRAIT);
		}

		if (floatAlign != null && widget instanceof HasFloat) {
			((HasFloat) widget).setFloat(
				floatAlign == Alignment.BEGIN ? Float.LEFT : Float.RIGHT);
		}

		if (duration != null) {
			if (widget instanceof HasDurationTransition) {
				((HasDurationTransition) widget).setDuration(duration);
			} else if (widget instanceof HasInOutDurationTransition) {
				((HasInOutDurationTransition) widget).setInDuration(duration);
				((HasInOutDurationTransition) widget).setOutDuration(duration);
			}
		}

		checkApplyLayoutVisibility(widget, style);
		checkApplyAlignment(widget, style);
		checkApplyIcon(widget, style);
	}

	/**
	 * Returns the default material widget animation.
	 *
	 * @return The default material widget animation type
	 */
	public static WavesType getDefaultAnimation() {
		return defaultWavesType;
	}

	/**
	 * Returns the current JQuery version.
	 *
	 * @return The JQuery version string
	 */
	public static native String getJQueryVersion() /*-{
        var query = $wnd['jQuery'];
        if (typeof query != 'undefined') {
            return query.fn.jquery;
        } else {
            return 'undefined';
        }
    }-*/;

	/**
	 * Initializes the library wrapper by registering the necessary factories.
	 */
	public static void init() {
		MaterialLayoutFactory layoutFactory = new MaterialLayoutFactory();

		EWT.setChildViewFactory(new MaterialChildViewFactory());
		EWT.setLayoutFactory(layoutFactory);
		EWT.setLayoutMapper(layoutFactory);

		Component.setWidgetStyleHandler(GewtMaterial::checkApplyStyles);
		Component.setWidgetErrorStateHandler(
			GewtMaterial::applyComponentErrorState);

		registerWidgetFactories();

		// register defaults for widgets not implemented by GewtMaterial
		EWT.registerDefaultWidgetFactories(false);
	}

	/**
	 * Initializes the GwtMaterialDesign-specific widget factories.
	 */
	private static void registerWidgetFactories() {
		MaterialListControlFactory listControlFactory =
			new MaterialListControlFactory();

		EWT.registerWidgetFactory(List.class, listControlFactory, true);
		EWT.registerWidgetFactory(ListBox.class, listControlFactory, true);

		EWT.registerWidgetFactory(Button.class, new MaterialButtonFactory<>(),
			true);
		EWT.registerWidgetFactory(CheckBox.class,
			new MaterialCheckBoxFactory<>(), true);
		EWT.registerWidgetFactory(RadioButton.class,
			new MaterialRadioButtonFactory(), true);
		EWT.registerWidgetFactory(Label.class, new MaterialLabelFactory<>(),
			true);
		EWT.registerWidgetFactory(TextArea.class,
			new MaterialTextAreaFactory(),
			true);
		EWT.registerWidgetFactory(TextField.class,
			new MaterialTextBoxFactory(),
			true);
		EWT.registerWidgetFactory(ProgressBar.class,
			new MaterialProgressBarFactory(), true);
		EWT.registerWidgetFactory(Table.class, new MaterialTableFactory(),
			true);
	}

	/**
	 * Sets the default material widget animation.
	 *
	 * @param wavesType The default material widget animation type
	 */
	public static void setDefaultAnimation(WavesType wavesType) {
		defaultWavesType = wavesType;
	}
}
