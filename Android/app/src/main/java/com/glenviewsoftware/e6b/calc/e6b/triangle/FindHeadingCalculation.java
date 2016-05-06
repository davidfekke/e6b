/*  PDAltCalculation.java
 *
 *  Created on Dec 24, 2012 by William Edward Woody
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

package com.glenviewsoftware.e6b.calc.e6b.triangle;

import java.util.List;

import com.glenviewsoftware.e6b.calc.CalcManager;
import com.glenviewsoftware.e6b.calc.CalcStorage;
import com.glenviewsoftware.e6b.calc.e6b.E6BCalcManager;
import com.glenviewsoftware.e6b.calc.e6b.E6BCalculation;
import com.glenviewsoftware.e6b.calc.e6b.planning.PlanCalculation;
import com.glenviewsoftware.e6b.units.Measurement;
import com.glenviewsoftware.e6b.units.Speed;
import com.glenviewsoftware.e6b.units.Units;
import com.glenviewsoftware.e6b.view.CalculatorInputView;

public class FindHeadingCalculation implements E6BCalculation
{

    @Override
    public String getCalculationName()
    {
        return "Heading for True Course";
    }

    @Override
    public CalcManager getCalculationManager()
    {
        return new E6BCalcManager(this);
    }

    @Override
    public String getCalculationDescription()
    {
        return "Given a wind speed and direction, the true air speed of your aircraft, and a desired (ground) true course, find the true heading you must fly to track the ground course.";
    }

    @Override
    public int getInputFieldCount()
    {
        return 4;
    }

    @Override
    public String getInputFieldName(int index)
    {
        switch (index) {
            default:
            case 0: return "Wind Direction";
            case 1: return "Wind Speed";
            case 2: return "(Ground) True Course";
            case 3: return "True Air Speed";
        }
    }

    @Override
    public Measurement getInputFieldUnit(int index)
    {
        switch (index) {
            default:
            case 0: return null;
            case 1: return Units.speed;
            case 2: return null;
            case 3: return Units.speed;
        }
    }

    @Override
    public int getOutputFieldCount()
    {
        return 2;
    }

    @Override
    public String getOutputFieldName(int index)
    {
        switch (index) {
            default:
            case 0: return "(Airplane) True Heading";
            case 1: return "Ground Speed";
        }
    }

    @Override
    public Measurement getOutputFieldUnit(int index)
    {
        switch (index) {
            default:
            case 0: return null;
            case 1: return Units.speed;
        }
    }

    @Override
    public void calculatorInitialize(List<CalculatorInputView> in, List<CalculatorInputView> out)
    {
        CalculatorInputView a = in.get(0);
        CalculatorInputView b = in.get(1);
        CalculatorInputView c = in.get(2);
        CalculatorInputView d = in.get(3);
        
        a.loadStoreValue(CalcStorage.shared().windDirection);
        b.loadStoreValue(CalcStorage.shared().windSpeed);
        c.loadStoreValue(CalcStorage.shared().groundCourse);
        d.loadStoreValue(CalcStorage.shared().trueAirSpeed);

        a = out.get(0);
        b = out.get(1);
        a.setUnit(0);
        b.setUnit(CalcStorage.shared().groundSpeed.getUnit());
    }

    @Override
    public void calculate(List<CalculatorInputView> in, List<CalculatorInputView> out, boolean store)
    {
        CalculatorInputView a = in.get(0);
        CalculatorInputView b = in.get(1);
        CalculatorInputView c = in.get(2);
        CalculatorInputView d = in.get(3);
        
        if (store) {
            CalcStorage.shared().windDirection = a.saveStoreValue();
            CalcStorage.shared().windSpeed = b.saveStoreValue();
            CalcStorage.shared().groundCourse = c.saveStoreValue();
            CalcStorage.shared().trueAirSpeed = d.saveStoreValue();
        }
        
        double av = a.getValue();
        double bv = b.getValueAsUnit(Speed.SPEED_KNOTS);
        double cv = c.getValue();
        double dv = d.getValueAsUnit(Speed.SPEED_KNOTS);
        
        PlanCalculation.FHResult r = PlanCalculation.FindHeading(cv, dv, av, bv);

        a = out.get(0);
        b = out.get(1);
        
        a.setValue(r.th);
        b.setValue(r.gs,Speed.SPEED_KNOTS);
        
        if (store) {
            CalcStorage.shared().airplaneCourse = a.saveStoreValue();
            CalcStorage.shared().groundSpeed = b.saveStoreValue();
        }
    }

}


