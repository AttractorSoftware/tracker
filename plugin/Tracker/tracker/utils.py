from pkg_resources import resource_filename
import glob


def calculate_client_package_path():
    resources_path = resource_filename(__name__, 'client') + "/"
    return glob.glob(resources_path + "*.zip")[0]


def calculate_client_package_name():
    return calculate_client_package_path().split("/")[-1]
