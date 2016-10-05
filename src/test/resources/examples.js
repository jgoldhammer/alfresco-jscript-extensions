//print(database.query("dataSource","select * from alf_node"));
//	print(repoAdmin.exec('show models'));

print(renditions.getRenditions(utils.getNodeFromString("workspace://SpacesStore/c5ac8e94-10e9-4b28-82b8-f48a430b3fe9")));

favorites.add(utils.getNodeFromString("workspace://SpacesStore/c5ac8e94-10e9-4b28-82b8-f48a430b3fe9"));
print(favorites.isFavorite(utils.getNodeFromString("workspace://SpacesStore/c5ac8e94-10e9-4b28-82b8-f48a430b3fe9")));

quickshare.shareContent(utils.getNodeFromString("workspace://SpacesStore/c5ac8e94-10e9-4b28-82b8-f48a430b3fe9"));
