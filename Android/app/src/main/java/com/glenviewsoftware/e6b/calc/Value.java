/*  Value.java
 *
 *  Created on Dec 19, 2012 by William Edward Woody
 */
/*	E6B: Calculator software for pilots.
 *
 *	Copyright © 2016 by William Edward Woody
 *
 *	This program is free software: you can redistribute it and/or modify it
 *	under the terms of the GNU General Public License as published by the
 *	Free Software Foundation, either version 3 of the License, or (at your
 *	option) any later version.
 *
 *	This program is distributed in the hope that it will be useful, but
 *	WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *	or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *	for more details.
 *
 *	You should have received a copy of the GNU General Public License along
 *	with this program. If not, see <http://www.gnu.org/licenses/>
 */

package com.glenviewsoftware.e6b.calc;

import java.io.Serializable;

public class Value implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int unit;
    private double value;
    
    public Value(double v, int u)
    {
        value = v;
        unit = u;
    }
    
    public Value(int u)
    {
        value = 0;
        unit = u;
    }

    public int getUnit()
    {
        return unit;
    }

    public double getValue()
    {
        return value;
    }
}

