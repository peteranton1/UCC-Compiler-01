from Style import *

# -------------------------
# Data definition
#
survey = {
    doc: "Eagle Surveys J111-111",
    "Q1": {
        qt: "A single choice Question",
        al: {
            "yes": { "Y": "Yes" },
            "no": { "N": "No" },
            "maybe": { "M": "Maybe" },
            "dkna" : { "D": "Dk/Na", "options": [single, "default"] },
        },
        options: ["single"]
    },
    "Q1Yes" : {
        qt : "Some other question",
        al : q1.al
    },
    "Q1no" : {
        qt : "Some other question",
        al : q1.al
    },
    "Numeric": {
        qt: "A numeric Question",
        al: {
            "num": { "%5d": "Enter Amount:" },
            "dkna" : { "DKNA": "Dk/Na", "options": ["single", "hidden"] },
        },
        options: ["single"]
    },
    "Dob": {
        qt: "A Date Question",
        al: {
            "dob": { "%2d/%2d/%4d": "DD/MM/YYYY:" },
            "dkna" : { "DKNA": "Dk/Na", "options": ["single", "hidden"] },
        },
        options: ["single"]
    },
    "Name": {
        qt: "A character Question",
        al: {
            "first": { "%25s": "First Name:" },
            "middle": { "%10s": "Middle Name:" },
            "last": { "%25s": "Last Name:" },
            "dkna" : { "DKNA": "Dk/Na", "options": ["single", "hidden"] },
        },
        options: ["single"]
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

    present(data.Numeric)
    present(data.Dob)
    present(data.Name)
#
# Data and Script ends here
# -------------------------
