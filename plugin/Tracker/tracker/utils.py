from pkg_resources import resource_filename
import glob


def calculate_client_package_path():
    try:
        filepath = resource_filename(__name__, 'client')
    except:
        filepath = ""
    resources_path = filepath + "/"
    client_dir_content = glob.glob(resources_path + "*.zip")
    if not client_dir_content:
        return ""
    else:
        return client_dir_content[0]


def calculate_client_package_name():

    path = calculate_client_package_path()
    if path == "":
        return path
    else:
        return path.split("/")[-1]
