from tracker import *

import os
import pkg_resources
path = os.path.abspath(os.path.join(os.path.dirname(__file__), '../..', '.python-eggs-cache'))
pkg_resources.set_extraction_path(path)