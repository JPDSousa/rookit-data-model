
package org.rookit.dm;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.rookit.api.dm.RookitModel;
import org.rookit.test.SerializationTest;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("javadoc")
public abstract class AbstractRookitTest<T extends RookitModel> extends AbstractDataModelTest<T>
        implements SerializationTest<T> {

    @Test
    public void testId() {
        final ObjectId id = new ObjectId();
        this.testResource.setId(id.toHexString());
        assertThat(this.testResource.getId())
                .get()
                .as("The assigned id")
                .isEqualTo(id.toHexString());
    }

}
