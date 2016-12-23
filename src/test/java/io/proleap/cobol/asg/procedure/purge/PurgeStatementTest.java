package io.proleap.cobol.asg.procedure.purge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import io.proleap.cobol.CobolTestSupport;
import io.proleap.cobol.asg.applicationcontext.CobolParserContext;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.purge.PurgeStatement;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

public class PurgeStatementTest extends CobolTestSupport {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void test() throws Exception {
		final File inputFile = new File("src/test/resources/io/proleap/cobol/asg/procedure/purge/PurgeStatement.cbl");
		final Program program = CobolParserContext.getInstance().getParserRunner().analyzeFile(inputFile,
				CobolSourceFormatEnum.TANDEM);

		final CompilationUnit compilationUnit = program.getCompilationUnit("PurgeStatement");
		final ProgramUnit programUnit = compilationUnit.getProgramUnit();
		final ProcedureDivision procedureDivision = programUnit.getProcedureDivision();
		assertEquals(0, procedureDivision.getParagraphs().size());
		assertEquals(1, procedureDivision.getStatements().size());

		{
			final PurgeStatement purgeStatement = (PurgeStatement) procedureDivision.getStatements().get(0);
			assertNotNull(purgeStatement);
			assertEquals(StatementTypeEnum.PURGE, purgeStatement.getStatementType());
			assertEquals(2, purgeStatement.getCommunicationDescriptionEntryCalls().size());

			{
				final Call cdEntryCall = purgeStatement.getCommunicationDescriptionEntryCalls().get(0);
				assertNotNull(cdEntryCall);
				assertEquals(Call.CallType.UNDEFINED_CALL, cdEntryCall.getCallType());
			}

			{
				final Call cdEntryCall = purgeStatement.getCommunicationDescriptionEntryCalls().get(1);
				assertNotNull(cdEntryCall);
				assertEquals(Call.CallType.UNDEFINED_CALL, cdEntryCall.getCallType());
			}
		}
	}
}