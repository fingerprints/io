/*
 * DelimetedTextToExcel.java
 *
 * Copyright (C) 2010 Fingerprints Software
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.fingerprintsoft.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @author Fuzail Sarang
 * 
 */
public class DelimetedTextToXLS {

    public static final DelimetedTextToXLS COMMA_SEPERATED_VALUE = new DelimetedTextToXLS(
            DelimitedTextFileReader.COMMA_SEPERATED_VALUE);
    public static final DelimetedTextToXLS TAB_SEPERATED_VALUE = new DelimetedTextToXLS(
            DelimitedTextFileReader.TAB_SEPERATED_VALUE);
    public static final DelimetedTextToXLS PIPE_SEPERATED_VALUE = new DelimetedTextToXLS(
            DelimitedTextFileReader.PIPE_SEPERATED_VALUE);
    public static final DelimetedTextToXLS SPACE_SEPERATED_VALUE = new DelimetedTextToXLS(
            DelimitedTextFileReader.TAB_SEPERATED_VALUE);

    private DelimitedTextFileReader reader;
    
    public DelimetedTextToXLS(DelimitedTextFileReader reader) {
        this.reader = reader;
    }

    public void execute(String inputFilePath, String outputFilePath)
            throws FileNotFoundException, IOException {
        Validate.notNull(inputFilePath);
        Validate.notNull(outputFilePath);
        List<List<String>> rows = this.reader.read(inputFilePath);
        XLSFileWriter.write(outputFilePath, rows);

    }

    public DelimitedTextFileReader getReader() {
        return reader;
    }

    public void setReader(DelimitedTextFileReader reader) {
        this.reader = reader;
    }

}
