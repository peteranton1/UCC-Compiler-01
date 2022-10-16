#
# -------------------------
# Module definitions
#

def present(data):
    pass

def compute(data, args):
    pass

def test(data, args):
    pass

# define constants
qt = "qt"
al = "al"
doc = "doc"
options = "options"

on_entry = {
    qt : f"Survey {data.doc}\n\n"
         f"Hello welcome to a few questions.",
    al : "Press any key to continue"
}

on_exit = {
    qt : f"Survey {data.doc}\n\n"
         f"Thanks for everything.",
    al : "Press any key to continue"
}

