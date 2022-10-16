from Style import *

# -------------------------
# Data definition
#
survey = {
    doc: "Eagle Surveys J123456",
    "q1": {
        qt: "A single choice Question",
        al: {
            "yes": { "Y": "Yes" },
            "no": { "N": "No" },
            "maybe": { "M": "Maybe" },
            "dkna" : { "D": "Dk/Na", "options": ["single", "default"] },
        },
        options: ["single"]
    },
    "Q1yes" : {
        qt : "Some other question",
        al : q1.al
    },
    "Q1no" : {
        qt : "Some other question",
        al : q1.al
    },
}

# -------------------------
# Script definition
#
def script():
    data = survey
    present(data.Q1)

    if test(data.Q1, ["no" or "dkna"]):
        compute(data.Q1Yes, "no")

    if test(data.Q1, ["yes" or "maybe"]):
        compute(data.Q1No, "yes")

#
# Data and Script ends here
# -------------------------
