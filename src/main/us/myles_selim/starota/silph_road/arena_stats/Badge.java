package us.myles_selim.starota.silph_road.arena_stats;

import com.google.gson.annotations.SerializedName;

public class Badge {

	private UserBadge UserBadge;
	private BadgeInside Badge;

	public UserBadge getUserBadge() {
		return UserBadge;
	}

	public BadgeInside getBadge() {
		return Badge;
	}

	public class BadgeInside {

		private String id;
		private String type_id;
		private String slug;
		private String name;
		private String description;
		private String image;
		@SerializedName("public")
		private String isPublic;
		private String created;
		private String modified;

		public String getId() {
			return id;
		}

		public String getType_id() {
			return type_id;
		}

		public String getSlug() {
			return slug;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public String getImage() {
			return image;
		}

		public String getPublic() {
			return isPublic;
		}

		public String getCreated() {
			return created;
		}

		public String getModified() {
			return modified;
		}

	}

	public static class UserBadge {

		private String user_id;
		private String badge_id;
		private String variant;
		private String count;
		private String created;
		private String modified;

		public String getUser_id() {
			return user_id;
		}

		public String getBadge_id() {
			return badge_id;
		}

		public String getVariant() {
			return variant;
		}

		public String getCount() {
			return count;
		}

		public String getCreated() {
			return created;
		}

		public String getModified() {
			return modified;
		}

	}

}