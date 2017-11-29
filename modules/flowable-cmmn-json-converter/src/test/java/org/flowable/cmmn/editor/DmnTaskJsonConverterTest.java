package org.flowable.cmmn.editor;

import org.flowable.cmmn.model.Case;
import org.flowable.cmmn.model.CmmnModel;
import org.flowable.cmmn.model.FieldExtension;
import org.flowable.cmmn.model.PlanItem;
import org.flowable.cmmn.model.PlanItemDefinition;
import org.flowable.cmmn.model.ServiceTask;
import org.flowable.cmmn.model.Stage;
import org.hamcrest.core.Is;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author martin.grofcik
 */
public class DmnTaskJsonConverterTest extends AbstractConverterTest {
    @Override
    protected String getResource() {
        return "test.dmnTaskModel.json";
    }

    @Override
    protected void validateModel(CmmnModel model) {
        Case caseModel = model.getPrimaryCase();
        assertEquals("dmnExportCase", caseModel.getId());
        assertEquals("dmnExportCase", caseModel.getName());

        Stage planModelStage = caseModel.getPlanModel();
        assertNotNull(planModelStage);
        assertEquals("casePlanModel", planModelStage.getId());

        PlanItem planItem = planModelStage.findPlanItemInPlanFragmentOrUpwards("planItem1");
        assertNotNull(planItem);
        assertEquals("planItem1", planItem.getId());
        assertEquals("dmnTask", planItem.getName());
        PlanItemDefinition planItemDefinition = planItem.getPlanItemDefinition();
        assertNotNull(planItemDefinition);
        assertTrue(planItemDefinition instanceof ServiceTask);
        ServiceTask serviceTask = (ServiceTask) planItemDefinition;
        assertEquals("dmn", serviceTask.getType());
        assertEquals("sid-F4BCA0C7-8737-4279-B50F-59272C7C65A2", serviceTask.getId());
        assertEquals("dmnTask", serviceTask.getName());

        FieldExtension fieldExtension = new FieldExtension();
        fieldExtension.setFieldName("decisionTaskThrowErrorOnNoHits");
        fieldExtension.setStringValue("false");
        assertThat(((ServiceTask) planItemDefinition).getFieldExtensions(), Is.is(Collections.singletonList(fieldExtension)));
    }
}