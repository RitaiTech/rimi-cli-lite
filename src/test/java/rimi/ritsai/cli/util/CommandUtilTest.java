package rimi.ritsai.cli.util;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import rimi.ritsai.cli.command.Exit;

public class CommandUtilTest {

	@Test
	public void givenAnCommandBeanWithAnnoationThenExtractMetaDataCorrectly() {
		SoftAssertions assertion = new SoftAssertions();
		Exit bean = new Exit();		
		assertion.assertThat(CommandUtil.getCommandName(bean)).as("Command name").isEqualTo("exit");
		assertion.assertThat(CommandUtil.getCommandGroup(bean)).as("Group").isEqualTo("");
		assertion.assertThat(CommandUtil.getCommandUsage(bean)).as("Usage").isEqualTo("");
		assertion.assertThat(CommandUtil.getCommandManual(bean)).as("Manual").isEqualTo(ResourceUtils.loadUtf8Text("META-INF/shell/docs/exit.txt", ""));
		
		ExecCommand exec = new ExecCommand();
		assertion.assertThat(CommandUtil.getCommandManual(exec)).as("Manual").isEqualTo(ResourceUtils.loadUtf8Text("META-INF/shell/docs/exit.txt", ""));
		assertion.assertThat(CommandUtil.compareForSorting(bean, new Exit())).as("Order with same name").isEqualTo(0);
		assertion.assertThat(CommandUtil.compareForSorting(bean, exec)).as("Order with diff name").isGreaterThan(0);
		assertion.assertAll();
	}

}
