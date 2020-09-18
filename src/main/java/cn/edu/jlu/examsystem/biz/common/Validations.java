package cn.edu.jlu.examsystem.biz.common;

import javax.validation.groups.Default;

/**
 * @author WangZeying 2020/9/19 17:59
 */
public class Validations {
    private Validations() {
    }

    public interface ValidateChoice extends Default {
    }

    public interface ValidateTF extends Default {
    }

    public interface ValidateMultiAnswerChoice extends Default {
    }

    public interface ValidateShortAnswer extends Default {
    }
}
