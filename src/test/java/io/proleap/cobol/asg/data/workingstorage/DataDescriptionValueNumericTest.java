package io.proleap.cobol.asg.data.workingstorage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import io.proleap.cobol.CobolTestSupport;
import io.proleap.cobol.asg.applicationcontext.CobolParserContext;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Literal;
import io.proleap.cobol.asg.metamodel.NumericLiteral;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.DataDivision;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntryGroup;
import io.proleap.cobol.asg.metamodel.data.datadescription.ValueInterval;
import io.proleap.cobol.asg.metamodel.data.workingstorage.WorkingStorageSection;
import io.proleap.cobol.asg.metamodel.valuestmt.LiteralValueStmt;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

public class DataDescriptionValueNumericTest extends CobolTestSupport {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() throws Exception {
		final File inputFile = new File(
				"src/test/resources/io/proleap/cobol/asg/data/workingstorage/DataDescriptionValueNumeric.cbl");
		final Program program = CobolParserContext.getInstance().getParserRunner().analyzeFile(inputFile,
				CobolSourceFormatEnum.TANDEM);

		final CompilationUnit compilationUnit = program.getCompilationUnit("DataDescriptionValueNumeric");
		final ProgramUnit programUnit = compilationUnit.getProgramUnit();
		final DataDivision dataDivision = programUnit.getDataDivision();
		final WorkingStorageSection workingStorageSection = dataDivision.getWorkingStorageSection();

		{
			final DataDescriptionEntryGroup dataDescriptionEntryItem = (DataDescriptionEntryGroup) workingStorageSection
					.findDataDescriptionEntry("ITEMNUMERIC");

			assertNotNull(dataDescriptionEntryItem);
			assertNotNull(dataDescriptionEntryItem.getValueClause());

			final ValueInterval valueInterval = dataDescriptionEntryItem.getValueClause().getValueIntervals().get(0);
			assertNotNull(valueInterval);

			{
				final ValueStmt fromValueStmt = valueInterval.getFromValueStmt();
				final LiteralValueStmt literalFromValueStmt = (LiteralValueStmt) fromValueStmt;
				final Literal literal = literalFromValueStmt.getLiteral();
				assertEquals(Literal.Type.NUMERIC, literal.getType());
				assertEquals(NumericLiteral.Type.DOUBLE, literal.getNumericLiteral().getType());
				assertEquals(23.4, literal.getNumericLiteral().getDoubleValue(), EPSILON);
			}
		}

		{
			final DataDescriptionEntryGroup dataDescriptionEntryItem = (DataDescriptionEntryGroup) workingStorageSection
					.findDataDescriptionEntry("ITEMINTEGER");

			assertNotNull(dataDescriptionEntryItem);
			assertNotNull(dataDescriptionEntryItem.getValueClause());

			final ValueInterval valueInterval = dataDescriptionEntryItem.getValueClause().getValueIntervals().get(0);
			assertNotNull(valueInterval);

			{
				final ValueStmt fromValueStmt = valueInterval.getFromValueStmt();
				final LiteralValueStmt literalFromValueStmt = (LiteralValueStmt) fromValueStmt;
				final Literal literal = literalFromValueStmt.getLiteral();
				assertEquals(Literal.Type.NUMERIC, literal.getType());
				assertEquals(NumericLiteral.Type.INTEGER, literal.getNumericLiteral().getType());
				assertEquals(42, literal.getNumericLiteral().getValue());
			}
		}
	}
}