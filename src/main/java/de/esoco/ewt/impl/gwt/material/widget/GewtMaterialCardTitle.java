package de.esoco.ewt.impl.gwt.material.widget;

import de.esoco.ewt.graphics.Icon;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCardTitle;

/********************************************************************
 * A {@link MaterialCardTitle} subclass that also implements {@link
 * ImageAttribute}.
 *
 * @author eso
 */
public class GewtMaterialCardTitle extends MaterialCardTitle
	implements ImageAttribute
{
	//~ Instance fields ----------------------------------------------------

	private Image rImage;

	//~ Methods ------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage()
	{
		return rImage;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage)
	{
		this.rImage = rImage;

		if (rImage instanceof Icon)
		{
			setIconType(IconType.valueOf(((Icon) rImage).getName()));
		}
	}
}